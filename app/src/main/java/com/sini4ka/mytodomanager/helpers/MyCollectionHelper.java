package com.sini4ka.mytodomanager.helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Created by sini4ka on 30.6.16.
 */
public class MyCollectionHelper {
    public interface ListItemFunction<T1, T2>{
        T2 apply(T1 item);
    };

    public static <T1, T2> List<T2> getPropList(List<T1> list, ListItemFunction<T1, T2> f){
        List<T2> newList = new ArrayList<T2>();

        for(T1 item : list){
            newList.add(f.apply(item));
        }

        return newList;
    }
}
