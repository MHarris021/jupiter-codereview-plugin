package edu.hawaii.ics.csdl.jupiter.file.serializers;

import java.io.File;


public interface ISerializer<T> {

	public T deserialize(File file) throws SerializerException;

	public void serialize(T object, File file)
			throws SerializerException;

}