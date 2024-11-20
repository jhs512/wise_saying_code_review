package com.ll;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ApplicationTest {

  @Test
  public void fileLoadTest () {
    Service controller = new Service();
    assertThat(controller.data).hasSize(2);
    assertThat(controller.data.getFirst().get("id")).isEqualTo("1");
    assertThat(controller.data.getFirst().get("content")).isEqualTo("명언 1");
  }

  @Test
  public void fileBuildTest () {
    Service controller = new Service();
    controller.build();
  }

  @Test
  public void createTest() {
    Service controller = new Service();
    ByteArrayInputStream in1 = new ByteArrayInputStream("엄\n준".getBytes());
    System.setIn(in1);
    controller.create();
    assertThat(controller.data.getLast().get("content")).isEqualTo("엄");
  }

  @Test
  public void listUpTest() {
    // ByteArrayOutputStream을 이용하여 출력을 가로채기
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));

    Service controller = new Service();
    controller.listUp();

    String actualOutput = outputStream.toString();

    assertThat(actualOutput).isEqualTo("번호 / 작가 / 명언\n" +
        "----------------------");
  }

  @Test
  public void deleteTest() {
    Service controller = new Service();
    controller.delete("5");
    assertThat(controller.data.toString()).isEqualTo("[]");
  }

  @Test
  public void updateTest() {

    ByteArrayInputStream in1 = new ByteArrayInputStream("수정\n완\n".getBytes());
    System.setIn(in1);

    Service controller = new Service();
    controller.update("5");

    assertThat(controller.data.getFirst().get("content")).isEqualTo("수정");
  }

  @Test
  public void searchTest() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));

    Service controller = new Service();
    controller.search("수정했어요", "content");
    String actualOutput = outputStream.toString();
    assertThat(actualOutput).isEqualTo("");



  }
  @Test
  public void appTest() {

    ByteArrayInputStream in1 = new ByteArrayInputStream("수정?id=1 \n 수정했어요 \n 용준짱".getBytes());
    System.setIn(in1);
  }
}
