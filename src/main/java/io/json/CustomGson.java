package io.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class CustomGson {
  private static final Gson INSTANCE = createGson();

  private CustomGson() {}

  public static Gson getInstance() {
    return INSTANCE;
  }

  private static Gson createGson() {
    return new GsonBuilder()
        .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
        .setPrettyPrinting().create();
  }

  private static class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Override
    public void write(JsonWriter output, LocalDateTime value) throws IOException {
      if (value == null) {
        output.nullValue();
      } else {
        output.value(FORMATTER.format(value));
      }
    }

    @Override
    public LocalDateTime read(JsonReader input) throws IOException {
      if (input.peek() == com.google.gson.stream.JsonToken.NULL) {
        input.nextNull();
      }
      return LocalDateTime.parse(input.nextString(), FORMATTER);
    }
  }

  private static class LocalDateAdapter extends TypeAdapter<LocalDate> {
    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    @Override
    public void write(JsonWriter output, LocalDate value) throws IOException {
      if (value == null) {
        output.nullValue();
      } else {
        output.value(FORMATTER.format(value));
      }
    }

    @Override
    public LocalDate read(JsonReader input) throws IOException {
      if (input.peek() == com.google.gson.stream.JsonToken.NULL) {
        input.nextNull();
        return null;
      }

    return LocalDate.parse(input.nextString(), FORMATTER);
    }
  }
}
