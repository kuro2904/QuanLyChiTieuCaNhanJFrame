package models;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Budget {
	private int budgetId;
	private String name;
	private float amount;
	private float expensed;
	private Recurrence recurrence;
	private Date startDate;
	private Date endDate;
	private int categoryId;
	private int userId;
}
