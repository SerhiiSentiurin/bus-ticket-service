package com.my.demo.util;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class PaymentIdSaver {
    private static final List<Long> listId = new ArrayList<>();
    public void saveId(Long id){
        listId.add(id);
    }
    public List<Long> getSavedId(){
        return listId;
    }
    public void deleteAllSavedId(List<Long> listIdForDelete){
        listId.removeAll(listIdForDelete);
    }
}
