package com.order_service.order_service.utils;

import com.order_service.order_service.exception.IllegalStateTransition;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
@Component
public class StateValidator {

    private static final Set<String> VALID_TRANSACTIONS = new HashSet<>();
    private static final Set<String> ADMIN_ONLY_TRANSACTIONS = new HashSet<>();
    static {
        VALID_TRANSACTIONS.add("PENDING->CANCELLED");
        VALID_TRANSACTIONS.add("RESERVED->CANCELLED");
        VALID_TRANSACTIONS.add("RESERVED->FAILED");
        VALID_TRANSACTIONS.add("RESERVED->CONFIRMED");
        VALID_TRANSACTIONS.add("PENDING->RESERVED");
    }
    static {
        ADMIN_ONLY_TRANSACTIONS.add("CONFIRMED->CANCELLED");
    }

    public static void  validate(String current,String target){
        String setState = current+"->"+target;
        if(!VALID_TRANSACTIONS.contains(setState)){
            throw new IllegalStateTransition("Invalid State Transition");
        }
    }

    public static void isAdminOnly(String current,String target){
        String setState = current+"->"+target;
        if(!ADMIN_ONLY_TRANSACTIONS.contains(setState)){
            throw new IllegalStateTransition("only admin can change this state");
        }
    }



}
