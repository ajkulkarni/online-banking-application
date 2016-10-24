package org.thothlab.devilsvault.dao.transaction;
import java.util.List;

import javax.sql.DataSource;

import org.thothlab.devilsvault.db.model.Request;
import org.thothlab.devilsvault.db.model.Transaction;
/**
 * Created by jaydatta on 10/16/16.
 */
public interface TransactionDao {

    public void setDataSource(DataSource dataSource);

    public int createTransaction();

    public Boolean save(Transaction transaction, String type);

    public Transaction getById(int id,String table);

    public void update(Transaction employer);

    public void deleteById(int id);

    public List<Transaction> getAllPending();

    public List<Transaction> getAllCompleted();
}
