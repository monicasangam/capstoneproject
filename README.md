# capstone-project
The project code is located in "us.analytiq.knime.qvx". This directory has code for both the QVX Reader and QVX Writer nodes.

QVX Reader: KNIME node that allows the user to select a qvx input file. On execution, this file is read by the node, and a BufferedDataTable is created on the output port.

QVX Writer: KNIME node that allows the user to select a qvx output file. The input port accepts a BufferedDataTable, and on execution, a qvx file is generated from this BufferedDataTable.

Running the project in KNIME:
Go to "Help" -> "Install New Software" and choose the directory named "us.analytiq.knime.qvx.update".
Alternatively: Copy the jar file from the "us.analytiq.knime.qvx.update.plugins" directory into  the "dropins" directory of the KNIME installation. Note: This project is compatible with KNIME 3.7.x.

Running the projects in Eclipse:
Before running the project, you must set up the KNIME Analytics SDK. Instructions are found here:
https://github.com/knime/knime-sdk-setup

There is another project in the root directory, called "JAXB Generation". We used JAXB to generate Java classes to help us deal with the XML-formatted QVX Table Header. We have already copied these generated classes into both the QvxReader and the QvxWriter projects. Thus, it is not necessary to run "JAXB Generation" in order to test our QvxReader and QvxWriter.

The "Documents" directory contains materials that were used through out the semester, such as our Final Showcase Presentations.

The "us.analytiq.knime.qvx.feature" directory contains licensing and copyright information.
