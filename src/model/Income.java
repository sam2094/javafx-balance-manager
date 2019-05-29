/*@author Samir Hasanov */
package model;

import java.util.Date;

public class Income {

    private int id;
    private String note;
    private int amount;
    private Date date;
    private IncomeCategory incomeCategory;

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

    public IncomeCategory getIncomeCategory() {
        return incomeCategory;
    }

    public void setIncomeCategory(IncomeCategory incomeCategory) {
        this.incomeCategory = incomeCategory;
    }

    @Override
    public String toString() {
        return "Income{" + "id=" + id + ", note=" + note + ", amount=" + amount + ", date=" + date + ", incomeCategory=" + incomeCategory + '}';
    }
}
