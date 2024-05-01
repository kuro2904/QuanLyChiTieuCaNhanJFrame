package models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
	private int categoryId;
	private String name;
	private boolean type;  // False is expense - True is income
	private	int userId;
	
}
