package com.myfields.kyle.pestsampler;

/**
 * This class is meant to host variables that need to be accessed throughout the project.
 */
public class Globals {

    // The current user variable will define the current user for the pest sampler. This
    // will need to be accessed from many places to add/edit samples, view fields, and
    // access credentials.
    public static User currentUser = null;

    // The sample to build variable will define the current pest sample being built via the
    // pest sampler. This will need to be accessed from all of the pest sampler pages,
    // and therefore must be declared global.
    public static PestSample sampleToBuild = null;

}
