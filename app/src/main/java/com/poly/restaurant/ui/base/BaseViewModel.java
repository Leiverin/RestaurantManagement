package com.poly.restaurant.ui.base;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.poly.restaurant.data.db.AppDatabase;
import com.poly.restaurant.data.db.dao.ProductDao;
import com.poly.restaurant.data.db.dao.TableDao;
import com.poly.restaurant.data.models.Product;
import com.poly.restaurant.data.models.Table;
import com.poly.restaurant.utils.helps.AppExecutors;

import java.util.List;

public abstract class BaseViewModel extends ViewModel {
    protected ProductDao productDao;
    protected TableDao tableDao;
    protected AppExecutors appExecutors;

    protected BaseViewModel(Context context) {
        productDao = AppDatabase.getInstance(context).productDao();
        tableDao = AppDatabase.getInstance(context).tableDao();
        appExecutors = new AppExecutors();
    }

    public List<Product> getListProductByIdTable(String idTable) {
        return productDao.getProductByIdTable(idTable);
    }

    public LiveData<List<Product>> getLocalProductsLiveData() {
        return productDao.getProducts();
    }

    public void insertProduct(Product product) {
        appExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                productDao.insertProduct(product);
            }
        });
    }

    public void updateProduct(Product product) {
        appExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                productDao.updateProduct(product);
            }
        });
    }

    public void updateAmountProduct(int amount, String id, String idTable) {
        appExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                productDao.updateAmount(amount, id, idTable);
            }
        });
    }

    public void deleteProduct(Product product) {
        appExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                productDao.deleteProduct(product);
            }
        });
    }

    public void deleteProduct(String id, String idTable) {
        appExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                productDao.deleteProduct(id, idTable);
            }
        });
    }

    public List<Product> getListProduct() {
        return productDao.getListProducts();
    }

    public String getProductById(String id, String idTable) {
        return productDao.findProductById(id, idTable);
    }
    // table

    public void insertTable(Table table) {
        appExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                tableDao.insertTable(table);
            }
        });
    }

    public void deleteTable(String id) {
        appExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                tableDao.deleteTable(id);
            }
        });
    }

    public void deleteTableWhenPaying(Table table) {
        appExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                tableDao.deleteTableWhenPaying(table);
            }
        });
    }

    public void updateTable(Table table) {
        appExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                tableDao.updateTable(table);
            }
        });
    }

    public void updateProductMerge(String idTable,String id){
        appExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                productDao.updateProductMerge(idTable,id);
            }
        });
    }


}
