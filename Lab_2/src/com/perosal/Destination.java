package com.perosal;

public class Destination {
    public final String name;
    private int currentSupply;
    public final int maxSupply;

    /** @param name is the name of destination, every destination
     *              has unique name
     *  @param maxSupply is the maximum supply it can get*/
    Destination(String name, int maxSupply) {
        this.name = name;
        this.maxSupply = maxSupply;
        this.currentSupply = 0;
    }

    /** It takes as parameter the maximum amount of a source.
     * @return the remaining amount of supply after it was pushed  */
    public int pushSupply(int amount) {

        int ableToPush = maxSupply - currentSupply;

        if (amount <= ableToPush) {
            currentSupply += amount;
            return 0;
        }

        currentSupply = maxSupply;
        amount -= ableToPush;

        return amount;
    }

    /** Check whether the destination is full. */
    public boolean isFull() {
        return currentSupply == maxSupply;
    }

    @Override
    public String toString() {
        return "Destination: " + "Name: " + name + " current supply: " + currentSupply + " max supply:" + maxSupply + " ";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Destination that = (Destination) o;
        return name.equals(that.name);
    }

}
