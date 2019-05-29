/*@author Samir Hasanov */
package repository;

import db.DataManager;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Income;
import model.IncomeCategory;

public class IncomeRepository extends DataManager implements Repository<Income> {

    @Override
    public void add(Income income) {
        String query = "insert into income (note,amount,date,category_id) values(?,?,?,?)";
        try {
            connect();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, income.getNote());
            preparedStatement.setInt(2, income.getAmount());
            preparedStatement.setDate(3, (Date) income.getDate());
            preparedStatement.setInt(4, income.getIncomeCategory().getId());
            preparedStatement.execute();
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                disconnect();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void update(Income income) {
    }

    @Override
    public void remove(Income income) {
    }

    @Override
    public List<Income> findAll() {
        List<Income> incomes = new ArrayList<>();
        String query = "select * from income left join income_category on income.category_id = income_category.id where income.status = 1 and income_category.status = 1";
        try {
            connect();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Income income = new Income();
                IncomeCategory incomeCategory = new IncomeCategory();
                income.setId(resultSet.getInt("income.id"));
                income.setNote(resultSet.getString("income.note"));
                income.setDate(resultSet.getDate("income.date"));
                income.setAmount(resultSet.getInt("income.amount"));
                incomeCategory.setId(resultSet.getInt("income_category.id"));
                incomeCategory.setName(resultSet.getString("income_category.name"));
                income.setIncomeCategory(incomeCategory);
                incomes.add(income);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                disconnect();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return incomes;
    }

        public List<Income> findForChart() {
        List<Income> incomes = new ArrayList<>();
        String query = "select * ,sum(amount) as sum from income left join income_category on income.category_id = income_category.id where income.status = 1 and income_category.status = 1 group by (category_id)";
        try {
            connect();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Income income = new Income();
                IncomeCategory incomeCategory = new IncomeCategory();
                income.setAmount(resultSet.getInt("sum"));
                incomeCategory.setName(resultSet.getString("income_category.name"));
                income.setIncomeCategory(incomeCategory);
                incomes.add(income);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                disconnect();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return incomes;
    }
    
    
    
    public List<Income> findByDate(Date startDate,Date endDate) {
        List<Income> incomes = new ArrayList<>();
        String query = "select * from income left join income_category on income.category_id = income_category.id where income.status = 1 and income_category.status = 1 and income.date between '"+startDate+"' and '"+endDate+"'";
        try {
            connect();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Income income = new Income();
                IncomeCategory incomeCategory = new IncomeCategory();
                income.setId(resultSet.getInt("income.id"));
                income.setNote(resultSet.getString("income.note"));
                income.setDate(resultSet.getDate("income.date"));
                income.setAmount(resultSet.getInt("income.amount"));
                incomeCategory.setId(resultSet.getInt("income_category.id"));
                incomeCategory.setName(resultSet.getString("income_category.name"));
                income.setIncomeCategory(incomeCategory);
                incomes.add(income);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                disconnect();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return incomes;
    }

}
