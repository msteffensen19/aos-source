package com.advantage.root.store.config;

/**
 * Created by kubany on 10/11/2015.
 */
public class EVG_AppInitializer_constants {


    private static final String LOCATION = "C:/temp/"; // Temporary location where files will be stored
    private static final long MAX_FILE_SIZE = 5242880; // 5MB : Max file size.
    // Beyond that size spring will throw exception.
    private static final long MAX_REQUEST_SIZE = 20971520; // 20MB : Total request size containing Multi part.
    private static final int FILE_SIZE_THRESHOLD = 0; // Size threshold after which files will be written to disk

}
