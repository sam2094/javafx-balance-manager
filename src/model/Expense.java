/*@author Samir Hasanov */
package model;

import java.util.Date;

public class Expense {

    private int id;
    private String note;
    private int amount;
    private Date date;
    private ExpenseCategory expenseCategory;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ExpenseCategory getExpenseCategory() {
        return expenseCategory;
    }

    @Override
    public String toString() {
        return "Expense{" + "id=" + id + ", note=" + note + ", amount=" + amount + ", date=" + date + ", expenseCategory=" + expenseCategory + '}';
    }

    public void setExpenseCategory(ExpenseCategory expenseCategory) {
        this.expenseCategory = expenseCategory;
    }
}
