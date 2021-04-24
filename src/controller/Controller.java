package controller;

import navigation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import java.io.IOException;
import apps.*;

import java.lang.Integer;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Controller {
	Scanner inputScanner = new Scanner(System.in);
	
	public void start(){
		createCLI();
		runApplication();
	}
	public void restart() {
		MainMenu.mainMenuList();
		runApplication();
		System.gc();
	}
	public void createCLI()
	{
		MainMenu.freshStartMenu();
	}
	public void runApplication(){
		
		String input = inputScanner.nextLine();
		int x = Integer.parseInt(input);
		
		if(x == 99) {
			System.exit(0);
		}
		
	//Now it is selecting the apps	
		//public ip lookup
		if( x == 1)
		{
			PublicIPLookup();
		}
		//Port Scanner
		if(x ==2) {
			PortScanner();
		}
		//AES Encrypt
		if(x == 3){
			AESEncrypt();
		}
		//AES Decrypt
		if(x == 4){
			AESDecrypt();
		}
		//MD5 Brute Force
		if(x == 5)
		{
			HashBruteForce();
		}
		if(x == 7)
		{
			SlowLoris();
		}
		if(x == 8)
		{
			HTTPServer();
		}
		if(x == 6)
		{
			BenchmarkHashRate();
		}
		
	}
	public void PublicIPLookup() {
		try {
			PublicIPLookupHost.main(null);
			restart();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			restart();
		}
	}
	public void PortScanner() {
		System.out.println("Input the IP you wish to scan (deafult is local host 127.0.0.1): ");
		String ip = inputScanner.nextLine();
		System.out.println("Input number of threads for port scan (default is 0 which is the fastest): ");
		String threads = inputScanner.nextLine();
		if(threads.equals(""))
		{
			threads = "0";
		}
		
		if(ip.equals("")) {
			try {
				System.out.println("Scanning local host 127.0.0.1 with " + threads+ " threads");
				PortScanner.localPortScan(threads);
				restart();
			} catch (UnknownHostException | InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				restart();
			}
		}
		
		try {
			System.out.println("Scanning "+ip+" with " + threads+ " threads");
			PortScanner.foreignPortScan(ip, threads);
			restart();
		} catch (UnknownHostException | InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			restart();
		}
	}
	public void AESEncrypt() {
		//Grab all input from user
		System.out.println("Path to file you would like to encrypt");
		String fileIn = inputScanner.nextLine();
		while(fileIn.equals(""))
		{
			System.out.println("You must input a file");	
			fileIn = inputScanner.nextLine();
		}
		System.out.println("Input the passphrase for the encryption (MUST BE 24 CHARACTERS LONG, Default is javulsisthebestthingever)");
		String passphrase = inputScanner.nextLine();
		System.out.println("Add file out path for encrypted file (default is same location with .enc extension");
		String fileOut = FilenameUtils.removeExtension(inputScanner.nextLine());
		
		//run tests against inputs
		if(passphrase.equals(""))
		{
			System.out.println("Using default passphrase (javulsisthebestthingever)");
			passphrase = "javulsisthebestthingever";
		}
		if(fileOut.equals(""))
		{
			System.out.println("Using default path out with .enc extension");	
			fileOut = FilenameUtils.removeExtension(fileIn) + ".enc";
		}
		
		//execute
		try {
			EncryptDecryptFilesAES.encryptedFile(passphrase, fileIn, fileOut);
			System.out.println("Encryption executed successfully :)");
			restart();
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
				| BadPaddingException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Encryption failed, this is so sad");
			restart();
		}
	}
	public void AESDecrypt(){
		//Grab all input from user
		System.out.println("Drag and drop the file that you would like to decrypt (path to file)");
		String fileIn = inputScanner.nextLine();
		if(fileIn.equals(""))
		{
			System.out.println("You must input a file");
			fileIn = inputScanner.nextLine();
		}
		System.out.println("Input the passphrase for the encryption (MUST BE 24 CHARACTERS LONG, Default is javulsisthebestthingever)");
		String passphrase = inputScanner.nextLine();
		System.out.println("Add file out path for decrypted file (default is same location with .txt extension");
		String fileOut = FilenameUtils.removeExtension(inputScanner.nextLine());
		
		//run tests against inputs
		if(passphrase.equals(""))
		{
			System.out.println("Using default passphrase (javulsisthebestthingever)");
			passphrase = "javulsisthebestthingever";
		}
		if(fileOut.equals(""))
		{
			System.out.println("Using default path out with .txt extension");	
			fileOut = fileIn + ".txt";
		}
		
		//execute
		try {
			EncryptDecryptFilesAES.decryptedFile(passphrase, fileIn, fileOut);
			System.out.println("Decryption executed successfully :)");
			restart();
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
				| BadPaddingException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Decryption failed, this is so sad");
			restart();
		}
		
	}
	
	public void SlowLoris() {
		System.out.println("Please input the website/host");
		String host = inputScanner.nextLine();
		while(host.equals(""))
		{
			System.out.println("You must input a website, host, or IP");	
			host = inputScanner.nextLine();
		}
		System.out.println("Input the port Number (default is 80)");
		String port = inputScanner.nextLine();
		System.out.println("Please input the number of threads you wish to use (defaut is 20000)");
		String threads = inputScanner.nextLine();
		System.out.println("Amount of time (in minutes, default is maximum [2147483647 min])");
		String time = inputScanner.nextLine();
				
		apps.SlowLoris.slowLorisRun(host, port, threads, time);
		restart();
	}
	public void HTTPServer() {
		System.out.println("Wouldyou like to run this in the background? (y/n)");
		String bg = inputScanner.nextLine();
		if(bg.equals("y"))
		{
			System.out.println("This feature will be added in the future... sorry");
			System.out.println("Please input the root directory of the HTTP server");
			String dir = inputScanner.nextLine();
			System.out.println("Please input the port number to listen on");
			String port = inputScanner.nextLine();
			System.out.println("Starting server");
			apps.HTTPServer.startServerUpInGUI(dir, port);
		}
		else {
			System.out.println("Please input the root directory of the HTTP server");
			String dir = inputScanner.nextLine();
			System.out.println("Please input the port number to listen on");
			String port = inputScanner.nextLine();
			System.out.println("Starting server");
			apps.HTTPServer.startServerUpInGUI(dir, port);
		}
		
	}
	public void HashBruteForce() {
			List<String> listOfHashes = new ArrayList<String>();
			System.out.println("Please input the number of hashes that you wish to crack");
			String numOfHashes = inputScanner.nextLine();
			int numohash = Integer.parseInt(numOfHashes);
			listOfHashes.add(numOfHashes);
			System.out.println("Please input the type of hash EXACTLY AS SEEN");
			System.out.println("Supported hashes (Default is MD5)"
					+ "MD2\n"
					+ "MD5\n"
					+ "SHA-1\n"
					+ "SHA-224\n"
					+ "SHA-256\n"
					+ "SHA-384\n"
					+ "SHA-512/224\n"
					+ "SHA-512/256");
			
			String typeOfHash = inputScanner.nextLine();
			if(!typeOfHash.equals("MD2") && !typeOfHash.equals("MD5") && !typeOfHash.equals("SHA-1") && !typeOfHash.equals("SHA-224") &&
					 !typeOfHash.equals("SHA-256") && !typeOfHash.equals("SHA-384") && !typeOfHash.equals("SHA-512/224") && !typeOfHash.equals("SHA-512/256"))
			{
				typeOfHash = "MD5";
			}
			for(int h = 0; numohash > h; h++)
			{
				
				System.out.println("Input Hash (" + (h+1) + "): ");
				String hash = inputScanner.nextLine();
				listOfHashes.add(hash);
			}
			System.out.println("Please enter the characters you would like to test. ");
			System.out.println("Simply type the letter option in lowercase, or type a lowercase y for 'yes'. Enter 1 option per line.");
			System.out.println("");
			System.out.println("AKA Type the letter in the parenthesesis if you want to add them to the char set");
			System.out.println("");
			System.out.println("Lower Case (l)");
			String l = inputScanner.nextLine();
			if(!l.equals("l"))
			{
				l = "none";
			}
			listOfHashes.add(l);
			System.out.println("Upper Case (u)");
			String u = inputScanner.nextLine();
			if(!u.equals("u"))
			{
				u = "none";
			}
			listOfHashes.add(u);
			System.out.println("Numbers (n)");
			String n = inputScanner.nextLine();
			if(!n.equals("n"))
			{
				n = "none";
			}
			listOfHashes.add(n);
			System.out.println("Special Characters (s) [i.e. !@#$% etc]");
			String s = inputScanner.nextLine();
			if(!s.equals("s"))
			{
				s = "none";
			}
			listOfHashes.add(s);
			listOfHashes.add(typeOfHash);

			System.out.println("Allow to run in background?(y?)");
			String background = inputScanner.nextLine();
			if(background.equals("y"))
			{
				System.out.println("This feature will be added in the future... sorry, non-verbose mode has started");
			}
			else
			{
				background = "no";
			}
			listOfHashes.add(background);

			//System.out.println("Cracking " + listOfHashes.size() + " Hashes");
			HashBruteForce.main(listOfHashes.toArray(new String[listOfHashes.size()]));
			restart();

	}
	public void BenchmarkHashRate()
	{
		System.out.println("Benchmark will test how long it takes for each algorythmn to get to 94^3 hashes or 830,584 guesses");
		System.out.println("");
		System.out.println("");
		System.out.println("");
		List<String> listOfHashes = new ArrayList<String>();
		String numOfHashes = "1";
		int numohash = Integer.parseInt(numOfHashes);
		listOfHashes.add(numOfHashes);
		String typeOfHash = "MD2";
		String hash = "887938f312078f5287d3114d3bb9d2d2";
		listOfHashes.add(hash);
		String l ="l";
		String u ="u";
		String n ="n";
		String s ="s";
		listOfHashes.add(l);
		listOfHashes.add(u);
		listOfHashes.add(n);
		listOfHashes.add(s);
		listOfHashes.add(typeOfHash);
		String background = "y";
		listOfHashes.add(background);
		String benchmark ="y";
		listOfHashes.add(benchmark);
		HashBruteForce.main(listOfHashes.toArray(new String[listOfHashes.size()]));
		listOfHashes.clear();
		System.out.println("");
		System.out.println("");
		
		listOfHashes.add(numOfHashes);
		typeOfHash = "MD5";
		hash = "102c3439d577d1d0d552b03fcae27c5e";
		listOfHashes.add(hash);
		listOfHashes.add(l);
		listOfHashes.add(u);
		listOfHashes.add(n);
		listOfHashes.add(s);
		listOfHashes.add(typeOfHash);
		listOfHashes.add(background);
		listOfHashes.add(benchmark);
		HashBruteForce.main(listOfHashes.toArray(new String[listOfHashes.size()]));
		listOfHashes.clear();
		System.out.println("");
		System.out.println("");
		
		listOfHashes.add(numOfHashes);
		typeOfHash = "SHA-1";
		hash = "40ef8e2f391a7167d3643c402aff5290167914a6";
		listOfHashes.add(hash);
		listOfHashes.add(l);
		listOfHashes.add(u);
		listOfHashes.add(n);
		listOfHashes.add(s);
		listOfHashes.add(typeOfHash);
		listOfHashes.add(background);
		listOfHashes.add(benchmark);
		HashBruteForce.main(listOfHashes.toArray(new String[listOfHashes.size()]));
		listOfHashes.clear();
		System.out.println("");
		System.out.println("");
		
		listOfHashes.add(numOfHashes);
		typeOfHash = "SHA-224";
		hash = "5e0cb235996b2b1c75af0454df59f52dce03a792959ee50ea9927865";
		listOfHashes.add(hash);
		listOfHashes.add(l);
		listOfHashes.add(u);
		listOfHashes.add(n);
		listOfHashes.add(s);
		listOfHashes.add(typeOfHash);
		listOfHashes.add(background);
		listOfHashes.add(benchmark);
		HashBruteForce.main(listOfHashes.toArray(new String[listOfHashes.size()]));
		listOfHashes.clear();
		System.out.println("");
		System.out.println("");
		
		listOfHashes.add(numOfHashes);
		typeOfHash = "SHA-256";
		hash = "a03f2fd631370334952c5db487ce810e6af747de720ed7a05543a4c1204d3998";
		listOfHashes.add(hash);
		listOfHashes.add(l);
		listOfHashes.add(u);
		listOfHashes.add(n);
		listOfHashes.add(s);
		listOfHashes.add(typeOfHash);
		listOfHashes.add(background);
		listOfHashes.add(benchmark);
		HashBruteForce.main(listOfHashes.toArray(new String[listOfHashes.size()]));
		listOfHashes.clear();
		System.out.println("");
		System.out.println("");
		
		
		listOfHashes.add(numOfHashes);
		typeOfHash = "SHA-384";
		hash = "04cc6681b210fca435c5fae5610ae021d72382ac8a3b1d6a366aa8e7c897d2cc60b21f011eeeab8a9263570648e3b309";
		listOfHashes.add(hash);
		listOfHashes.add(l);
		listOfHashes.add(u);
		listOfHashes.add(n);
		listOfHashes.add(s);
		listOfHashes.add(typeOfHash);
		listOfHashes.add(background);
		listOfHashes.add(benchmark);
		HashBruteForce.main(listOfHashes.toArray(new String[listOfHashes.size()]));
		listOfHashes.clear();
		System.out.println("");
		System.out.println("");
		
		listOfHashes.add(numOfHashes);
		typeOfHash = "SHA-512/224";
		hash = "444e291be9148571c9b3f03c94b2482f24d3b15bc96cb3901616117b";
		listOfHashes.add(hash);
		listOfHashes.add(l);
		listOfHashes.add(u);
		listOfHashes.add(n);
		listOfHashes.add(s);
		listOfHashes.add(typeOfHash);
		listOfHashes.add(background);
		listOfHashes.add(benchmark);
		HashBruteForce.main(listOfHashes.toArray(new String[listOfHashes.size()]));
		listOfHashes.clear();
		System.out.println("");
		System.out.println("");
		
		listOfHashes.add(numOfHashes);
		hash = "f0dbd2a766db0c870542c2adadc28cf548d9f3b4be6e5e29fbf0ee03d3b163c5";
		typeOfHash = "SHA-512/256";
		listOfHashes.add(hash);
		listOfHashes.add(l);
		listOfHashes.add(u);
		listOfHashes.add(n);
		listOfHashes.add(s);
		listOfHashes.add(typeOfHash);
		listOfHashes.add(background);
		listOfHashes.add(benchmark);
		HashBruteForce.main(listOfHashes.toArray(new String[listOfHashes.size()]));
		listOfHashes.clear();
		System.out.println("");
		System.out.println("");
		
		
		
		
		restart();
		
		
	}



}
