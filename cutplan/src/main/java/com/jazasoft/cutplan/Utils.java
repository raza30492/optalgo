package com.jazasoft.cutplan;

import com.jazasoft.cutplan.model.Size;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mdzahidraza on 02/10/17.
 */
public class Utils {

    public static int getNoOfPly(Map<Integer,Integer> order, List<Size> sizeList) {
        if (order == null || sizeList == null) return 0;
        int noOfPly = Integer.MAX_VALUE;

        for (Size size: sizeList) {
            if (size.getRatio() !=null && size.getRatio().getRatio() != 0) {
                int ply = order.get(size.getSize()) / size.getRatio().getRatio();
                if (ply < noOfPly){
                    noOfPly = ply;
                }
            }
        }
        return noOfPly;
    }

    public static Map<Integer, Integer> getRemainingOrder(Map<Integer,Integer> order, List<Size> sizeList) {
        Map<Integer,Integer> remainingOrder = new HashMap<>();
        int noOfPly = getNoOfPly(order,sizeList);
        for (Size size: sizeList) {
            int ratio = size.getRatio() != null ? size.getRatio().getRatio() : 0;
            remainingOrder.put(size.getSize(), order.get(size.getSize()) - (noOfPly*ratio));
        }
        return remainingOrder;
    }
}
