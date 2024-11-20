package com.ll;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.*;

public class ApplicationTest {
  public void setInput(String input) {

  ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
  System.setIn(in);

  try {
    in.close();
  } catch (IOException e) {
    System.out.println("처리되지 못한 스트림");
  }
  }

  public ByteArrayOutputStream setOutput() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));
    return outputStream;
  }

  public String getOutput(ByteArrayOutputStream outputStream) {
    String output = outputStream.toString();
    try {
      outputStream.close();
    } catch (IOException e) {
      System.out.println("처리되지 못한 스트림");
    }
    return output;
  }


  @Test
  public void fileLoadTest () {
    Service service = new Service(true);
    assertThat(service.data).hasSize(0);
  }

  @Test
  public void fileBuildTest () {
    Service service = new Service();
    service.build();
  }

  @Test
  public void createTest() {
    setInput("테스트명언\n테스트저자");
    Controller controller = new Controller(true);
    controller.create();
  }

  @Test
  public void listUpTest() {
    // ByteArrayOutputStream을 이용하여 출력을 가로채기
    ByteArrayOutputStream stream = setOutput();

    Console.args = "page=2";
    Controller controller = new Controller(true);
    controller.listUp();

    String actualOutput = getOutput(stream);
    assertThat(actualOutput).isEqualTo("번호 / 작가 / 명언\n" +
        "----------------------");
  }

  @Test
  public void deleteTest() {
    Controller controller = new Controller(true);
    Console.args = "id=4&keyword=123";
    controller.delete();
  }

  @Test
  public void updateTest() {
    setInput("수\n완");
    Console.args = "id=6&keyword=123";
    Controller controller = new Controller(true);
    controller.update();
//    assertThat(controller.data.getFirst().get("content")).isEqualTo("수정");
  }

  @Test
  public void searchTest() {
    ByteArrayOutputStream stream = setOutput();
    Console.args = "keywokeywor&&dType=autho";
    Controller controller = new Controller(true);
    controller.search();

    String output = getOutput(stream);
    assertThat(output).isEqualTo("");
  }
  @Test
  public void appTest() {
    ByteArrayInputStream in1 = new ByteArrayInputStream("수정?id=1 \n 수정했어요 \n 용준짱".getBytes());
    System.setIn(in1);
  }
}
