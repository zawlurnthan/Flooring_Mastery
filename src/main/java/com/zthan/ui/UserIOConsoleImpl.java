package com.zthan.ui;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

@Component
public class UserIOConsoleImpl implements UserIO{
    final private Scanner console = new Scanner(System.in);

    @Override
    public void print(String msg) {
        System.out.println(msg);
    }


    @Override
    public String readString(String prompt) {
        System.out.println(prompt);
        return console.nextLine();
    }

    public boolean askQuestion(String message) {
         return readString(message).equalsIgnoreCase("y");
    }


    @Override
    public int readInt(String prompt) {
        boolean invalidInput = true;
        int num = 0;
        while (invalidInput) {
            try {
                // print message Prompt
                String stringValue = this.readString(prompt);
                // get input line, and parse
                num = Integer.parseInt(stringValue);
                invalidInput = false;
            } catch (NumberFormatException e) {
                this.print("Input error, Please try again.");
            }
        }
        return num;
    }

    @Override
    public int readInt(String prompt, int min, int max) {
        int result;
        do {
            result = this.readInt(prompt);
        } while (result < min || result > max);

        return result;
    }


    @Override
    public float readFloat(String prompt) {
        boolean invalidInput = true;
        float num = 0;
        while (invalidInput) {
            try {
                // print message Prompt
                String stringValue = this.readString(prompt);
                // get input line, and parse
                num = Float.parseFloat(stringValue);
                invalidInput = false;
            } catch (NumberFormatException e) {
                this.print("Input error, Please try again.");
            }
        }
        return num;
    }


    @Override
    public float readFloat(String prompt, float min, float max) {
        float result;
        do {
            result = this.readFloat(prompt);
        } while (result < min || result > max);

        return result;
    }

    @Override
    public double readDouble(String prompt) {
        boolean invalidInput = true;
        double num = 0;
        while (invalidInput) {
            try {
                // print message Prompt
                String stringValue = this.readString(prompt);
                // get input line, and parse
                num = Double.parseDouble(stringValue);
                invalidInput = false;
            } catch (NumberFormatException e) {
                this.print("Input error, Please try again.");
            }
        }
        return num;
    }

    @Override
    public double readDouble(String prompt, double min, double max) {
        double result;
        do {
            result = this.readDouble(prompt);
        } while (result < min || result > max);

        return result;
    }

    @Override
    public long readLong(String prompt) {
        boolean invalidInput = true;
        long num = 0;
        while (invalidInput) {
            try {
                // print message Prompt
                String stringValue = this.readString(prompt);
                // get input line, and parse
                num = Long.parseLong(stringValue);
                invalidInput = false;
            } catch (NumberFormatException e) {
                this.print("Input error, Please try again.");
            }
        }
        return num;
    }

    @Override
    public long readLong(String prompt, long min, long max) {
        long result;
        do {
            result = this.readLong(prompt);
        } while (result < min || result > max);

        return result;
    }

    @Override
    public String readDate() {
        boolean invalid = true;
        String formatted = "";

        // loop if input is invalid
        while (invalid) {
            String prompt = readString("\nEnter a date in format as MM/dd/yyyy");

            // try and catch mismatch pattern of enter date
            try {
                // format date
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                // get input date
                LocalDate ld = LocalDate.parse(prompt, formatter);
                // convert date to string
                formatted = ld.format(formatter);

                invalid = false;
            } catch (DateTimeParseException e) {
                print("Text could not be parsed. Enter the date in exact format!");
            }
        }
        // take forward slash out from string
        String[] tokens = formatted.split("/");
        // join strings together and return
        return String.join("", tokens);
    }

    @Override
    public String readFutureDate() {
        boolean invalid = true;
        String formatted = "";
        LocalDate today = LocalDate.now();

        // loop if input is invalid
        while (invalid) {
            String prompt = readString("\nEnter a future date in formatted as MM/dd/yyyy");

            // try and catch mismatch pattern of enter date
            try {
                // format date
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                // get input date
                LocalDate date = LocalDate.parse(prompt, formatter);
                // check if input date is not in future
                if (date.compareTo(today) <= 0) {
                    throw new NotFutureDateException("Date is not in the future!");
                }
                // convert date to string
                formatted = date.format(formatter);
                invalid = false;
            }
            catch (DateTimeParseException e) {
                print("Text could not be parsed. Enter the date in exact format!");
            }
            catch (NotFutureDateException e) {
                print(e.getMessage());
            }
        }
        // take forward slash out from string
        String[] tokens = formatted.split("/");
        // join strings together and return
        return String.join("", tokens);
    }

}
