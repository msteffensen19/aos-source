package com.advantage.online.store.image;

import java.io.IOException;

public interface ManagedImage {

	ImageManagement getImageManagement();
	String getId();
	String getType();
	String getFileName();
	byte[] getContent() throws IOException;
}