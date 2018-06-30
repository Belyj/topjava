package ru.javawebinar.topjava.model;

public class MealWithExceedUI {

    private String dateTime;

    private String description;

    private String calories;

    private String exceedColor;

    public MealWithExceedUI(MealWithExceed mealWithExceed) {
        this.dateTime = mealWithExceed.getDateTime().toString().replace("T", " ");
        this.description = mealWithExceed.getDescription();
        this.calories = String.valueOf(mealWithExceed.getCalories());
        this.exceedColor = mealWithExceed.isExceed() ? "red" : "green";
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public String getCalories() {
        return calories;
    }

    public String getExceedColor() {
        return exceedColor;
    }
}
