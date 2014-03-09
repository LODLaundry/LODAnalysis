package mappers;

import java.io.IOException;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/* ================================================ */
public class GetSchemaStatsMapper extends Mapper<LongWritable, Text, Text, NullWritable> {

  private NullWritable nothing = NullWritable.get();
  private Text oKey = new Text();
  /* ----------------------------------- */
  private final String delim = "\\s?(<|\")";
  /* ----------------------------------- */
  private boolean validTriple (String str) {
    return str.matches ("^"                       +
                        "\\s?(\".*\"\\^\\^)?<.*>" +
                        "\\s+(\".*\"\\^\\^)?<.*>" +
                        "\\s+(\".*\"\\^\\^)?<.*>" +
                        "\\s+\\."                 +
                        "$");
  }
  /* ----------------------------------- */
  private boolean isSchema (String term) {
    return term.matches ("^.*http://www.w3.org/.*/(rdf-syntax-ns|rdf-schema|owl|XMLSchema)#.*$");
  }
  /* ----------------------------------- */
  @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
      /*
       * Convert the triple to String, so that we can check it
       * more easily
       */
      String inputTriple = value.toString();
      /*
       * Drop all triples that are not in the format we expect.
       */
      if (! validTriple (inputTriple)) return;
      /*
       * Now we split the triples into [s, p, o].
       */
      String[] terms = inputTriple.split(delim);
      /* ----------------------------------- */
      for (int i = 0; i < terms.length; i++) {
        if (isSchema (terms[i])) {
          oKey.set (terms[i]);
          context.write (oKey, nothing);
        }
      }
    }
}
