package com.epsilon.vtr.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.epsilon.vtr.model.Employee;
import com.epsilon.vtr.model.Item;

@Repository("itemDao")
public class ItemDaoImpl extends AbstractDao<Integer, Item> implements ItemDao {

    @Override
    public Item findById(int id) {
        return getByKey(id);
    }

    @Override
    public void saveItem(Item item) {
        persist(item);
    }

    @Override
    public void deleteItemById(int id) {
        Query query = getSession().createSQLQuery("delete from Item where id = :id");
        query.setInteger("itemName", id);
        query.executeUpdate();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Item> findAllItems() {
        Criteria criteria = createEntityCriteria();
        return (List<Item>) criteria.list();
    }

    @Override
    public List<Item> findItemsThatDoesHasTrailPhoto(Employee employee) {
        return null;
    }
}
