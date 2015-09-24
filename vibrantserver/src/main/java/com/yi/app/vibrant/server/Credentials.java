package com.yi.app.vibrant.server;

import java.net.URLEncoder;
import java.util.ArrayList;

public class Credentials {
 private ArrayList<String> list = new ArrayList<String>();

 public void set(String name, String value) {
  StringBuilder buffer = new StringBuilder();

  buffer.append(name);
  buffer.append("=");
  buffer.append(getUTF8String(value));

  add(buffer.toString());
 }

 public void append(String name, String value) {
  StringBuilder buffer = new StringBuilder();

  buffer.append("&");
  buffer.append(name);
  buffer.append("=");
  buffer.append(getUTF8String(value));

  add(buffer.toString());
 }

 private void add(String item) {
  list.add(item);
 }

 private String getUTF8String(String value) {
  String encodedValue = null;

  try {
   encodedValue = URLEncoder.encode(value, "UTF-8");
  } catch(Exception exception) {
   System.err.println("Encoding error");
  }

  return encodedValue;
 }

 public boolean isEmpty() {
  return list.isEmpty();
 }

 public void reset() {
  list.clear();
 }

 public String getUserCredentials() {
  StringBuilder buffer = new StringBuilder();
  int size = list.size();

  for(int i = 0; i < size; i++)
   buffer.append(list.get(i));

  return buffer.toString();
 }
}
