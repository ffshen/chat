package org.pliers.chat;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author shenx
 * @Description: TODO
 * @date 2018/3/24
 */
public class Test {

    public static void main(String[] args) throws Exception{
        run();
        resultRecod.stream()
                .parallel()
                .distinct()
                .forEach(r->System.out.println("END:"+Arrays.toString(r) +",weight:"+calculateTotalWeight(r)+",error:"+(calculateTotalWeight(r)-weight)));
    }
    //模拟称重，将递归改成循环，提高效率
    //
    private static int[] weights    = {120,187,220,274,311,340,450,550} ;
    private static int[] amounts    = {4,2,5,6,2,3,7,4} ;
    private static int[] records    = {0,0,0,0,0,0,0,0} ;
    private static int   maxAmount  = 10 ;
    private static int n = 8 ;
    private static List<int[]> resultRecod = new ArrayList<>() ;
    private static int weight = 1004 ;
    private static int error = 10 ;

    private static int findActiveNode(){
        for(int i = 1 ; i< n ; i++){
            if(records[i]>0) return i ;
        }
        return -1 ;
    }

    //计算某节点以下重量之和
    private static int calculateTotalWeight(int[] r){
        int totalWeight = 0 ;
        for(int index=0 ;index < n ;index++){
            totalWeight += r[index] * weights[index] ;
        }
        return totalWeight ;
    }

    //计算某节点以下重量之和
    private static int calculateTotalWeight(int i){
        int totalWeight = 0 ;
        for(int index=0 ;index < i ;index++){
            totalWeight += records[index] * weights[index] ;
        }
        return totalWeight ;
    }

    private static int calculateAmount(int i ,int weight){
        int returnAmount = 0;
        int recordNum = records[i] ;
        int amount = amounts[i];
        if(recordNum == 0){
            returnAmount = amount ;
        }
        else{
            returnAmount = recordNum ;
        }
        int remainder = weight % weights[i] ;
        int maxAmount = weight / weights[i] ;
        if(maxAmount == 0 || remainder !=0 ){
            maxAmount +=1 ;
        }
        if(maxAmount < returnAmount){
            returnAmount = maxAmount ;
        }
        return returnAmount ;
    }

    private static void clearRecord(int i){
        for(int index=0 ;index < i ;index++){
            records[index] = 0 ;
        }
    }

    private static void run(){
        int tmpWeight = 0 ;
        for(int i = n - 1 ; i >= 0  ; i-- ){//商品数量
            System.out.println("record:"+Arrays.toString(records)+",tmpWeight:"+tmpWeight);
            //数量 = Min {商品数量，（总重-临时重量）/单位重量 ,records[i] }
            int amount = calculateAmount(i,tmpWeight) ;
            //剩余重量=总重-单位重量*.大于0. ,将j记录在records里面
            if( (weight - tmpWeight) > weights[i] * amount ){
                records[i] = amount ;
                tmpWeight += weights[i] * amount ;
            }
            //如果没有else算出来估算永远只能预估小于总重的.大于总重的情况算不出来
            else{
                if(Math.abs(weight - tmpWeight - weights[i] * amount ) < error) {
                    int[] recordclone = records.clone();
                    recordclone[i] = amount;
                    System.out.println("OK:"+Arrays.toString(recordclone)+",tmpWeight:"+calculateTotalWeight(recordclone));
                    resultRecod.add(recordclone) ;
                }
            }
            //
            //如果临时重量达到总重，那么记录下来,上下浮动10
            if(Math.abs(tmpWeight - weight) < error){
                System.out.println("OK:"+Arrays.toString(records)+",tmpWeight:"+tmpWeight);
                resultRecod.add(records.clone()) ;
            }
            //如果临时重量未达到总重，继续下一步试探.continue.
            //如果到底或者不可再分小于一个最轻重量
            if(i == 0 || (weight - tmpWeight) < weights[0]){
                //如果临时重量未达到总重，且i等于商品数量-1
                //那么做以下判断
                //当前组合.是否存在活结.如果存在，寻找上一活结，重新计算临时重量，将i更新成该活结下标
                //否则.结束
                int actioveNode = findActiveNode() ;
                if(actioveNode < 0 ){
                    continue;
                }
                else{
                    i = actioveNode ;
                    //减1
                    tmpWeight -= weights[i] + calculateTotalWeight(i);
                    records[i]--;
                    //清空
                    clearRecord(i);
                }
            }
        }
    }

}
