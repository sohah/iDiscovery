package svcomp.Sanitizers6;// SPDX-FileCopyrightText: 2021 Falk Howar falk.howar@tu-dortmund.de
// SPDX-License-Identifier: Apache-2.0

// This file is part of the SV-Benchmarks collection of verification tasks:
// https://gitlab.com/sosy-lab/benchmarking/sv-benchmarks

import svcomp.micro.mockx.servlet.http.HttpServletRequest;
import svcomp.micro.mockx.servlet.http.HttpServletResponse;
import svcomp.micro.securibench.micro.sanitizers.Sanitizers6;

import java.io.IOException;

public class Main {

  public static void main(String[] args) {
    String s1 = "<bad/>";
    HttpServletRequest req = new HttpServletRequest();
    HttpServletResponse res = new HttpServletResponse();
    req.setTaintedValue(s1);

    Sanitizers6 sut = new Sanitizers6();
    try {
      sut.doGet(req, res);
    } catch (IOException e) {

    }
  }
}
