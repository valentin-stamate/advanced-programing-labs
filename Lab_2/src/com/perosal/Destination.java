package com.perosal;

public class Destination {
    private final String name;
    private int currentSupply;
    private int maxSupply;

    /** @param name is the name of destination, every destination
     *              has unique name
     *  @param maxSupply is the maximum supply it can get*/
    Destination(String name, int maxSupply) {
        this.name = name;
        this.currentSupply = 0;
        setMaxSupply(maxSupply);
    }

    /** Getter for maxSupply */
    public int getMaxSupply() {
        return maxSupply;
    }

    /** Setter for maxSupply */
    private void setMaxSupply(int maxSupply) {
        this.maxSupply = maxSupply;
    }

    /** Getter for name */
    public String getName() {
        return name;
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
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Destination that = (Destination) object;
        return name.equals(that.name);
    }
}
