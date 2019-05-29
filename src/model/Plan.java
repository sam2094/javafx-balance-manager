/*@author Samir Hasanov */
package model;

import java.util.Date;
import javafx.beans.property.DoubleProperty;

public class Plan {

    private int id;
    private String name;
    private Date startDate;
    private Date endDate;
    private int totalAmount;
    private String categoryName;
    private int categoryAllotmentAmount; // выделеннае на данную категорю сумма
    private int categorySpentAmount;  // общая потраченная сумма данной категории
    private double progress;
    private double procent;
    private String temp;

    public double getProcent() {
        return procent;
    }

    public void setProcent(double progress) {
        this.procent =(int)Math.round(progress*100);
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(double progress) {
        progress*=100;
        if(progress>=0 && progress<=33){
            this.temp = "Good";
        }
        if(progress>33 && progress<=66){
            this.temp = "Normal";
        }
        if(progress>66 && progress<=100){
            this.temp = "Bad";
        }if(progress>100){
            this.temp = "Very bad";
        }
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCategoryAllotmentAmount() {
        return categoryAllotmentAmount;
    }

    public void setCategoryAllotmentAmount(int categoryAllotmentAmount) {
        this.categoryAllotmentAmount = categoryAllotmentAmount;
    }

    public int getCategorySpentAmount() {
        return categorySpentAmount;
    }

    public void setCategorySpentAmount(int categorySpentAmount) {
        this.categorySpentAmount = categorySpentAmount;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return  name;
    }
}
