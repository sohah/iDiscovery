// SPDX-FileCopyrightText: 2021 Falk Howar falk.howar@tu-dortmund.de
// SPDX-License-Identifier: Apache-2.0

// This file is part of the SV-Benchmarks collection of verification tasks:
// https://gitlab.com/sosy-lab/benchmarking/sv-benchmarks

package svcomp.micro.mock.sql;

public class Connection {

  public void prepareStatement(String s) throws SQLException {
    if (s.contains("<bad/>")) {
      assert false;
    }
  }

  public void close() throws SQLException {}

  public Statement createStatement() {
    return new Statement();
  }
}
