/*@author Samir Hasanov */
package util;

import java.util.List;
import model.Expense;
import model.Income;
import repository.ExpenseRepository;
import repository.IncomeRepository;

public class MathOperation {

    public int getTotalAmount() {
        IncomeRepository incomeRepository = new IncomeRepository();
        ExpenseRepository expenseRepository = new ExpenseRepository();
        List<Income> incomeList = incomeRepository.findAll();
        List<Expense> expenceList = expenseRepository.findAll();
        int incomesTotal = 0;
        int expenceTotal = 0;
        int totalAmount = 0;
        for (Income i : incomeList) {
            incomesTotal += i.getAmount();
        }
        for (Expense e : expenceList) {
            expenceTotal += e.getAmount();
        }
        totalAmount = incomesTotal - expenceTotal;
        return totalAmount;
    }
}
