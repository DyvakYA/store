package model.dao.daofactory;

import model.dao.*;

public interface DaoManager {

    ProductDao createProductDao();

    UserDao createUserDao();

    OrderDao createOrderDao();

    UserOrderDao createUserOrderDao();

    OrderProductDao createOrderProductDao();

    void setDaoFactory(DaoFactory daoFactory);
}
