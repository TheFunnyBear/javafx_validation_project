package com.kosolapova.javafx.validation;

import java.util.ArrayList;
import java.util.List;

/**
 * Модель данных пользователей
 */
public class UserDataModel {
    private String name;
    private String password;
    private List<String> selectedItems;
    private boolean isAgreed;

    // Сеттеры
    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSelectedItems(List<String> selectedItems) {
        this.selectedItems = selectedItems;
    }

    public void setIsAgreed(boolean isAgreed) {
        this.isAgreed = isAgreed;
    }

    // Геттеры (если нужны)
    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public List<String> getSelectedItems() {
        return selectedItems;
    }

    public boolean isAgreed() {
        return isAgreed;
    }

    // Метод validate() остаётся без изменений
    public List<String> validate() {
        List<String> errors = new ArrayList<>();
        if (name == null || name.isEmpty()) errors.add("Имя не указано.");
        if (password == null || password.length() < 8) errors.add("Пароль слишком короткий.");
        if (selectedItems == null || selectedItems.isEmpty()) errors.add("Необходимо выбрать хотя бы один пункт.");
        if (!isAgreed) errors.add("Нужно подтвердить согласие.");
        return errors;
    }
}
