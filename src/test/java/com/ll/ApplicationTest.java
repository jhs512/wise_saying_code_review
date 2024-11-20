package com.ll;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ApplicationTest {

  @Test
  public void fileLoadTest () {
    Controller controller = new Controller();
    assertThat(controller.data).hasSize(2);
    assertThat(controller.data.getFirst().get("id")).isEqualTo("1");
    assertThat(controller.data.getFirst().get("content")).isEqualTo("명언 1");
  }

  @Test
  public void fileBuildTest () {
    Controller controller = new Controller();
    controller.build();
  }

  @Test
  public void createTest() {
    Controller controller = new Controller();
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

    Controller controller = new Controller();
    controller.listUp();

    String actualOutput = outputStream.toString();

    assertThat(actualOutput).isEqualTo("번호 / 작가 / 명언\n" +
        "----------------------");
  }

  @Test
  public void deleteTest() {
    Controller controller = new Controller();
    controller.delete("5");
    assertThat(controller.data.toString()).isEqualTo("[]");
  }

  @Test
  public void updateTest() {

    ByteArrayInputStream in1 = new ByteArrayInputStream("수정\n완\n".getBytes());
    System.setIn(in1);

    Controller controller = new Controller();
    controller.update("5");

    assertThat(controller.data.getFirst().get("content")).isEqualTo("수정");
  }

  @Test
  public void appTest() {

    ByteArrayInputStream in1 = new ByteArrayInputStream("수정?id=1 \n 수정했어요 \n 용준짱".getBytes());
    System.setIn(in1);

    App app = new App();
    app.execute();
  }
}
