package edu.pdx.cs410J.np4.client;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;
import com.google.gwt.user.client.rpc.impl.ReflectionHelper;

@SuppressWarnings("deprecation")
public class Airline_FieldSerializer implements com.google.gwt.user.client.rpc.impl.TypeHandler {
  private static native java.util.Collection getFlights(edu.pdx.cs410J.np4.client.Airline instance) /*-{
    return instance.@edu.pdx.cs410J.np4.client.Airline::flights;
  }-*/;
  
  private static native void setFlights(edu.pdx.cs410J.np4.client.Airline instance, java.util.Collection value) 
  /*-{
    instance.@edu.pdx.cs410J.np4.client.Airline::flights = value;
  }-*/;
  
  public static void deserialize(SerializationStreamReader streamReader, edu.pdx.cs410J.np4.client.Airline instance) throws SerializationException {
    setFlights(instance, (java.util.Collection) streamReader.readObject());
    
    edu.pdx.cs410J.AbstractAirline_FieldSerializer.deserialize(streamReader, instance);
  }
  
  public static edu.pdx.cs410J.np4.client.Airline instantiate(SerializationStreamReader streamReader) throws SerializationException {
    return new edu.pdx.cs410J.np4.client.Airline();
  }
  
  public static void serialize(SerializationStreamWriter streamWriter, edu.pdx.cs410J.np4.client.Airline instance) throws SerializationException {
    streamWriter.writeObject(getFlights(instance));
    
    edu.pdx.cs410J.AbstractAirline_FieldSerializer.serialize(streamWriter, instance);
  }
  
  public Object create(SerializationStreamReader reader) throws SerializationException {
    return edu.pdx.cs410J.np4.client.Airline_FieldSerializer.instantiate(reader);
  }
  
  public void deserial(SerializationStreamReader reader, Object object) throws SerializationException {
    edu.pdx.cs410J.np4.client.Airline_FieldSerializer.deserialize(reader, (edu.pdx.cs410J.np4.client.Airline)object);
  }
  
  public void serial(SerializationStreamWriter writer, Object object) throws SerializationException {
    edu.pdx.cs410J.np4.client.Airline_FieldSerializer.serialize(writer, (edu.pdx.cs410J.np4.client.Airline)object);
  }
  
}
