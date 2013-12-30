package io.recom.opinosis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import com.opinosis.Node;
import com.opinosis.OpinosisCore;
import com.opinosis.OpinosisGraphBuilder;
import com.opinosis.OpinosisMain;
import com.opinosis.summarizer.BasicSummarizer;

public class Opinosis {
	public Opinosis() {

	}

	public Opinosis(String propfile) {
		OpinosisMain opinosisMain = new OpinosisMain();
		opinosisMain.loadProps(propfile);
	}

	public String summarize(String fullText) throws IOException {
		SimpleDirectedWeightedGraph<Node, DefaultWeightedEdge> g = new SimpleDirectedWeightedGraph<Node, DefaultWeightedEdge>(
				DefaultWeightedEdge.class);
		OpinosisGraphBuilder<Node> builder = new OpinosisGraphBuilder<Node>();
		HashMap<String, Node> wordNodeMap = null;

		StringReader stringReader = new StringReader(fullText);
		BufferedReader reader = new BufferedReader(stringReader);

		String str = "";
		int sentenceid = 0;

		while ((str = reader.readLine()) != null) {
			sentenceid++;
			str = str.toLowerCase();
			wordNodeMap = builder.growGraph(str, 1, sentenceid);
		}

		g = builder.getGraph();

		StringWriter printer = new StringWriter();

		OpinosisCore summarizer = new BasicSummarizer(g, wordNodeMap, printer);
		summarizer.start();

		// System.gc();

		return printer.toString();
	}

	public static void main(String[] args) throws IOException {
		Opinosis opinosis = new Opinosis();
		String fullText = "the/DT rooms/NNS were/VBD very/RB nice/JJ ./.\nthe/DT rooms/NNS were/VBD nice/JJ ./.\nthe/DT rooms/NNS were/VBD cool/JJ ./.\nthe/DT rooms/NNS were/VBD cool/JJ ./.\nthe/DT toilet/NNS were/VBD cool/JJ ./.\nthe/DT toilet/NNS were/VBD cool/JJ ./.\nthe/DT toilet/NNS were/VBD clean/JJ ./.\nvery/RB good/JJ selection/NN of/IN food/NN ./.\nvery/RB good/JJ selection/NN of/IN food/NN for/IN breakfast/NN buffet/NN ./.\nits/PRP$ a/DT chain/NN but/CC the/DT food/NN was/VBD cheap/JJ and/CC delicious/JJ !/.\nthe/DT food/NN delicious/JJ and/CC reasonable/JJ ./.\nthe/DT food/NN was/VBD delicious/JJ in/IN both/CC the/DT consortia/NN and/CC forum/NN restaurants/NNS in/IN the/DT hotel/NN ./.\na/JJ b/JJ c/NN ./.\na/JJ b/JJ c/NN ./.\n";
		System.out.println(opinosis.summarize(fullText));
	}

}
