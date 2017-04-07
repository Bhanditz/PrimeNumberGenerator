package com.github.open96.primenumbergenerator.bitset;

import java.math.BigInteger;
import java.util.BitSet;

/**
 * Created by end on 07/04/17.
 */
public class BitSetContainer {
    private final long containerSize;
    private BitSet[] container;
    private int numberOfContainers;


    public boolean get(long position){
            long positionInContainer;
            long containerNumber;
            if(position>Integer.MAX_VALUE){
                containerNumber= position/Integer.MAX_VALUE;
                positionInContainer = position - Integer.MAX_VALUE*containerNumber;
            }
            else{
                positionInContainer=position;
                containerNumber=0;
            }
            if(positionInContainer<0)
        System.out.println(containerNumber+" "+positionInContainer+" "+position);
            return container[(int)containerNumber].get((int)positionInContainer);
    }


    public boolean set(long position, boolean value){
        if(position<containerSize){
            long positionInContainer;
            long containerNumber;
            if(position>Integer.MAX_VALUE){
                containerNumber= position/Integer.MAX_VALUE;
                positionInContainer = position - Integer.MAX_VALUE*(containerNumber);
            }
            else{
                positionInContainer=position;
                containerNumber=0;
            }
            container[(int)containerNumber].set((int)positionInContainer,value);
            return true;
        }
        return false;
    }

    private void populateContainer(){
        for(int x=0;x<numberOfContainers;x++){
            if(x!=numberOfContainers-1){
                container[x]=new BitSet(Integer.MAX_VALUE);
                for(long y=0;y<=Integer.MAX_VALUE;y++){
                    container[x].set((int)y);
                }
            }else{
                if(numberOfContainers==1) {
                    container[x] = new BitSet((int) containerSize);
                    for (long y = 0; y <= (int) containerSize; y++) {
                        container[x].set((int) y);
                    }
                }
                else {
                    container[x]=new BitSet((int)(containerSize-(numberOfContainers-1)*Integer.MAX_VALUE));
                        for(long y=0;y<=(int)containerSize-numberOfContainers*Integer.MAX_VALUE;y++){
                            container[x].set((int)y);
                }
            }
        }
    }
    }

    private void createContainer(){
        numberOfContainers = (int)(containerSize/Integer.MAX_VALUE+1);
        container=new BitSet[numberOfContainers];
        populateContainer();
    }

    public BitSetContainer(long size){
        containerSize=size;
        createContainer();
    }
}
