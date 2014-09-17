package lodanalysis.metrics.voids;

import java.io.File;
import java.io.IOException;

import lodanalysis.Settings;
import lodanalysis.metrics.DescriptionCreator;
import lodanalysis.metrics.DescriptionsFactory;
import lodanalysis.metrics.DescriptionsFactory.Namespace;

import org.apache.commons.io.FileUtils;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;


public class NumDistinctSubjects extends DescriptionCreator {


	public NumDistinctSubjects(DescriptionsFactory factory) {
		super(factory);
	}

	@Override
	public void createDescription() throws IOException {
		doc.addProperty(getProp(Namespace.VOID, "distinctSubjects"), FileUtils.readFileToString(new File(dir, Settings.FILE_NAME_SUBJECT_COUNT)), XSDDatatype.XSDlong);
	}

}
