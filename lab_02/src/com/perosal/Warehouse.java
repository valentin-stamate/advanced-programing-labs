package com.perosal;

/** Overrides the class Source to have more types of <b>Sources</b>. */
public class Warehouse extends Source{

    Warehouse(String name, int supply) {
        super(name, SourceType.WAREHOUSE, supply);
    }
}
