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
  static int field;
  static int field2;

    @Fuzz
    public void mainTest(InputStream input) throws IOException {
    Verifier.input = input;
    int x = Verifier.nondetInt();

    MainTest inst = new MainTest();
    field = Verifier.nondetInt();
    inst.test(x, field, field2);
    // test(x,x);
  }
  /* we want to let the user specify that this method should be symbolic */

  /*
   * test IF_ICMPLE, IADD, INEG & IMUL  bytecodes
   */
  public void test(int x, int z, int r) {
    System.out.println("Testing ExSymExe19");
    int y = 3;
    x = z + r;
    z = y * x;
    r = -z;
    if (x > 99) System.out.println("branch FOO1");
    else {
      assert false;
      System.out.println("branch FOO2");
    }
    if (r > z) System.out.println("branch BOO1");
    else System.out.println("branch BOO2");

    // assert false;
  }
}
