package com.perosal;

/** Overrides the class Source to have more types of <b>Sources</b>. */
public class Factory extends Source{

    Factory(String name, int supply) {
        super(name, SourceType.FACTORY, supply);
    }
}
