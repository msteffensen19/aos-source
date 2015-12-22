package com.advantage.account.store.image;

import java.io.IOException;

public interface ManagedImage {

    ImageManagement getImageManagement();

    String getId();

    String getType();

    String getManagedFileName();

    String getOriginalFileName();

    byte[] getContent() throws IOException;
}