package com.epam.rd.autocode.hashtableopen816;

import java.util.Arrays;

public interface HashtableOpen8to16 {
    void insert(int key, Object value);
    Object search(int key);
    void remove (int key);
    int size();
    int[] keys();

    static HashtableOpen8to16 getInstance(){
        return new HashtableOpen8to16() {
            int fullness = 0;
            Integer[] keysFact = new Integer[8];
            {
                Arrays.fill(keysFact, null);
            }
            @Override
            public void insert(int key, Object value) {
                if (search(key)!= null) return;
                int place;
                if (fullness == 16)
                    throw new IllegalStateException();
                if (fullness == size())
                    keysFact = resizeTableInteger(size()*2);
                place = hashPlace(key,keysFact);
                keysFact[place] = Integer.parseInt(String.valueOf(value));
                fullness++;
            }
            public int hashPlace(int key, Integer[] map){
                int place;
                for (int i = 0; i < map.length; i++) {
                    place = (Math.abs(key)+i)%map.length;
                    if (map[place] == null) return place;
                }
                return -1;
            }
            Integer[] resizeTableInteger(int newSize){
                Integer[] tmpArray = Arrays.copyOf(keysFact,size());
                Integer[] newKeys = new Integer[newSize];
                Arrays.fill(newKeys,null);
                for (Integer integer : tmpArray) {
                    if (integer != null) {
                        int place = hashPlace(integer, newKeys);
                        newKeys[place] = integer;
                    }
                }
                return newKeys;
            }

            @Override
            public Object search(int key) {
                int place;
                for (int i = 0; i < size(); i++) {
                    place = (Math.abs(key)+i)%size();
                    if (keysFact[place]!= null && keysFact[place] == key) return keysFact[place];
                }
                return null;
            }

            @Override
            public void remove(int key) {
                int place;
                for (int i = 0; i < keysFact.length; i++) {

                    place = (Math.abs(key)+i)% keysFact.length;
                    if (place==-1) return;
                    if (keysFact[place]!= null && keysFact[place] == key){
                        keysFact[place] = null;
                        break;
                    }
                    if (i == keysFact.length-1) return;
                }
                fullness--;
                if (fullness <= (size()/4) && size()!=2){
                    keysFact = resizeTableInteger(size()/2);
                }
            }

            @Override
            public int size() {
                return keysFact.length;
            }

            @Override
            public int[] keys() {
                return changeInteger();
            }
            public int[] changeInteger(){
                int[] tmp = new int[keysFact.length];
                for (int i = 0; i < tmp.length; i++) {
                    if (keysFact[i] == null)
                        tmp[i] = 0;
                    else tmp[i] = keysFact[i];
                }
                return tmp;
            }
        };
    }

}

