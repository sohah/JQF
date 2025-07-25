import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import org.sosy_lab.sv_benchmarks.Verifier;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;
import org.junit.runner.RunWith;

/*
 * Origin of the benchmark:
 *     repo: https://babelfish.arc.nasa.gov/hg/jpf/jpf-symbc
 *     branch: updated-spf
 *     root directory: src/tests/gov/nasa/jpf/symbc
 * The benchmark was taken from the repo: 24 January 2018
 */
/*
 * Copyright (C) 2014, United States Government, as represented by the
 * Administrator of the National Aeronautics and Space Administration.
 * All rights reserved.
 *
 * Symbolic Pathfinder (jpf-symbc) is licensed under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// package gov.nasa.jpf.symbc;
import org.sosy_lab.sv_benchmarks.Verifier;

@RunWith(JQF.class)
public class MainTest {

    @Fuzz
    public void mainTest(InputStream input) throws IOException {
    Verifier.input = input;
    new MainTest().unboxed(Verifier.nondetInt(), 2);
    new MainTest().boxed(Verifier.nondetInt(), 2);
    new MainTest().customBoxed(Verifier.nondetInt(), 2);
  }

  private void unboxed(int i, int j) {
    if (i == j) {
      System.out.println("HIT");
    }
  }

  private void boxed(int i, int j) {
    if (new Integer(i).equals(new Integer(j))) {
      System.out.println("HIT");
    } else assert false;
  }

  private void customBoxed(int i, int j) {
    if (new MyInteger(i).equals(new MyInteger(j))) {
      System.out.println("HIT");
    }
  }
}

class MyInteger {
  int value;

  public MyInteger(int value) {
    this.value = value;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof MyInteger)) {
      return false;
    }
    MyInteger other = (MyInteger) obj;
    return this.value == other.value;
  }
}
