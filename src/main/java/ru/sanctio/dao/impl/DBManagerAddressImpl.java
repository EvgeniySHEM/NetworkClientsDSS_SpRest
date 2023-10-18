package ru.sanctio.dao.impl;//package ru.sanctio.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.sanctio.dao.DBManagerAddress;
import ru.sanctio.models.entity.Address;

import java.util.List;

@Repository
public class DBManagerAddressImpl implements DBManagerAddress {

    private final SessionFactory sessionFactory;

    @Autowired
    public DBManagerAddressImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Address> getAllInformation() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Address ad join fetch ad.client", Address.class).getResultList();
    }

    @Override
    public Address selectAddressById(String addressId) {
        Session session = sessionFactory.getCurrentSession();
        return session.find(Address.class, addressId);
    }

    @Override
    public void deleteAddressById(String addressId) {
        Address address = selectAddressById(addressId);
        int clientId = -1;

        if(address != null) {
            clientId = address.getClient().getClientId();
        }

        Session session = sessionFactory.getCurrentSession();
        session.remove(address);

        if (!checkAddressByClientId(clientId)) {
            deleteClientWithoutAddressById(clientId);
        }
    }

    private boolean checkAddressByClientId(int clientId) {
        if(clientId == -1) {
            return true;
        }
        Session session = sessionFactory.getCurrentSession();

        List<Address> list = session.createNativeQuery("SELECT * FROM address WHERE clientId = :clientId",
                        Address.class)
                .setParameter("clientId", clientId)
                .getResultList();

        return !list.isEmpty();
    }

    private void deleteClientWithoutAddressById(int clientId) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(clientId);
    }
}
