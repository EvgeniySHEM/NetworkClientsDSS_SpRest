
package ru.sanctio.dao.impl;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.sanctio.dao.DBManagerClient;
import ru.sanctio.models.entity.Address;
import ru.sanctio.models.entity.Client;

import java.util.List;
import java.util.Optional;

@Repository
public class DBManagerClientImpl implements DBManagerClient {

    private final SessionFactory sessionFactory;
    private final EntityManagerFactory entityManagerFactory;

    @Autowired
    public DBManagerClientImpl(SessionFactory sessionFactory, EntityManagerFactory entityManagerFactory) {
        this.sessionFactory = sessionFactory;
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public boolean createNewClient(Client newClient, Address newAddress) {
        newClient = createClientEntity(newClient);
        newAddress.setClient(newClient);
        return createAddressEntity(newAddress);
    }

    private Client createClientEntity(Client newClient) {
        Optional<Client> client = findClientNotById(newClient);

        return client.orElseGet(() -> {
            Session session = sessionFactory.getCurrentSession();
            return session.merge(newClient);
        });
    }

    private Optional<Client> findClientNotById(Client client) {
        Session session = sessionFactory.getCurrentSession();
        List<Client> clientList = session.createQuery("from Client c where c.clientName = :clientName and " +
                        "c.type = :type and c.added = :added", Client.class)
                .setParameter("clientName", client.getClientName())
                .setParameter("type", client.getType())
                .setParameter("added", client.getAdded())
                .getResultList();

        if (!clientList.isEmpty()) {
            return Optional.of(clientList.get(0));
        } else {
            return Optional.empty();
        }
    }

    private boolean createAddressEntity(Address newAddress) {
        Optional<Address> address = findAddressNotById(newAddress);

        if (address.isPresent()) {
            return false;
        } else {
            Session session = sessionFactory.getCurrentSession();
            session.persist(newAddress);
            return true;
        }
    }

    private Optional<Address> findAddressNotById(Address newAddress) {
        Session session = sessionFactory.getCurrentSession();

        List<Address> addressList = session.createQuery("from Address a where a.ip = :ip and a.mac = :mac " +
                        "and a.model = :model and a.address = :address", Address.class)
                .setParameter("ip", newAddress.getIp())
                .setParameter("mac", newAddress.getMac())
                .setParameter("model", newAddress.getModel())
                .setParameter("address", newAddress.getAddress())
                .getResultList();

        if (!addressList.isEmpty()) {
            return Optional.of(addressList.get(0));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public boolean update(Client newClient, Address newAddress) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(newClient);
        session.merge(newAddress);
        return true;
    }

    @Override
    public Client getClientById(String id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return entityManager.find(Client.class, id);
    }
}
