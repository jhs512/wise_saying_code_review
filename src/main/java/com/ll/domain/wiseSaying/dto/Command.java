package com.ll.domain.wiseSaying.dto;

import com.ll.global.exception.IdNotFoundException;
import com.ll.global.exception.InvalidCommandInputException;

public class Command {
    private final String command;
    private long id;
    private String keywordType = "";
    private String keyword = "";
    private int page = 1;

    public Command(String input) {
        if (input.contains("?")) {
            String[] commands = input.split("\\?", 2);
            this.command = commands[0];

            if (this.command.equals("목록")) {
                if (commands[1].startsWith("page=")) {
                    String[] splits = commands[1].split("&");
                    String pageStr = splits[0];
                    String temp = pageStr.split("=", 2)[1];
                    try {
                        if (!temp.isEmpty()) {
                            this.page = Integer.parseInt(temp);
                        }
                    } catch (NumberFormatException e) {
                        throw new InvalidCommandInputException("page 값을 제대로 입력해주세요");
                    }
                    if (splits.length > 1) {
                        StringBuilder sb = new StringBuilder();
                        int n = splits.length - 1;
                        for (int i = 1; i < splits.length; i++) {
                            sb.append(splits[i]);
                            if (i != n) {
                                sb.append("&");
                            }
                        }

                        this.splitSearch(sb.toString());
                    }
                } else {
                    this.splitSearch(commands[1]);
                }
            } else {
                this.id = this.splitCommand(commands);
            }
        } else {
            if (input.equals("삭제") || input.equals("수정")) {
                throw new IdNotFoundException(input + "?id= 형식으로 id 값을 입력해주세요.");
            }
            this.command = input;
        }
    }

    public long splitCommand(String[] commands) {
        if (!isValid(commands)) {
            throw new IdNotFoundException(commands[0] + "?id= 형식으로 id 값을 입력해주세요.");
        }

        String[] restCommands;

        try {
            restCommands = commands[1].split("=", 2);
            return Integer.parseInt(restCommands[1]);
        } catch (NumberFormatException e) {
            throw new IdNotFoundException("id 값을 제대로 입력해주세요");
        }
    }

    public void splitSearch(String commands) {
        String[] splits = commands.split("&");

        if (!isSearchLengthValid(splits)) {
            throw new InvalidCommandInputException("(page= &)keywordType= & keyword= 형식으로 입력해주세요.");
        }

        String[] split = splits[0].split("=", 2);

        if (splits.length == 1) {
            if (!isSearchKeyValid(split)) {
                throw new InvalidCommandInputException("keywordType= & keyword= 형식으로 입력해주세요.");
            }

            String key = split[0];
            if (key.equals("keywordType")) {
                this.keywordType = split[1];
            } else {
                this.keyword = split[1];
            }
        } else {
            String key = split[0];
            if (!key.equals("keywordType")) {
                throw new InvalidCommandInputException("keywordType= & keyword= 형식으로 입력해주세요.");
            }
            this.keywordType = split[1];

            split = splits[1].split("=", 2);
            key = split[0];
            if (!key.equals("keyword")) {
                throw new InvalidCommandInputException("keywordType= & keyword= 형식으로 입력해주세요.");
            }
            this.keyword = split[1];
        }
    }

    public boolean isSearchKeyValid(String[] inputs) {
        String input = inputs[0];
        if (!input.equals("keyword") && !input.equals("keywordType")) {
            return false;
        }

        return true;
    }

    public boolean isSearchLengthValid(String[] inputs) {
        String input = inputs[0];
        if (inputs.length > 2 || (!input.startsWith("keyword=") && !input.startsWith("keywordType="))) {
            return false;
        }

        return true;
    }

    public boolean isValid(String[] commands) {
        if (!commands[1].startsWith("id=")) {
            return false;
        }

        return true;
    }

    public String getCommand() {
        return this.command;
    }

    public long getId() {
        return this.id;
    }

    public String getKeywordType() {
        return this.keywordType;
    }

    public String getKeyword() {
        return this.keyword;
    }

    public int getPage() {
        return this.page;
    }
}
