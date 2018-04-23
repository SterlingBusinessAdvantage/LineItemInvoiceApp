import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * Create an application that allows users to create an invoice application.
 *Users should be able to input data until they run out and then the application 
 *will print out an invoice of all the data including a subtotal, tax and grand total.
 *Bonus: Add an extra field to the inputs (untaxable). 
 *This value will be inputted as either true or false. 
 *This will affect whether tax will be charged on that item or not. 
 *The invoice should now have:
 * taxable subtotal: 1000
 * untaxable subtotal: 100
 * tax: 90
 * Grand Total: 1190
 */

public class InvoiceApplication2 {

	public static void main(String[] args) {
		double taxRate;

		// ask user for a tax rate
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Please input tax rate");
		taxRate = keyboard.nextDouble();

		// format for 2 decimal places
		DecimalFormat df = new DecimalFormat("#.00");

		// default of hasMoreItem
		boolean hasMoreItem = true;

		// create an invoice to store all items
		ArrayList<LineItem> invoice = new ArrayList<LineItem>();

		while (hasMoreItem) {

			// create a new lineitem
			LineItem lineItem = new LineItem();

			// get inputs from users
			System.out.println("Please enter item number");
			lineItem.setItemNumber(keyboard.next());

			keyboard.nextLine();
			System.out.println("Please enter item description");
			lineItem.setItemDescription(keyboard.nextLine());

			System.out.println("Please enter item price");
			lineItem.setPrice(keyboard.nextDouble());

			System.out.println("Please enter quantity");
			lineItem.setQuantity(keyboard.nextDouble());

			System.out.println("taxable? true/false");
			lineItem.setTaxable(keyboard.nextBoolean());

			lineItem.setLineTotal();

			// add lineitem to invoice
			invoice.add(lineItem);

			// ask user if there is anymore lineitem
			System.out.println("Is there anymore item? Enter \"Y\" or \"N\"");
			if (keyboard.next().toUpperCase().equals("Y")) {
				hasMoreItem = true;
			} else {
				hasMoreItem = false;
			}

		}

		// print out the invoice when done inputing lineitems
		System.out
				.println("------------------------------------------Invoice-----------------------------------------");
		System.out.format("%15s%15s%20s%15s%10s%15s%15s", "Line Number",
				"Item Number", "Item Description", "Quantity", "Price",
				"Line Total", "Taxable");
		System.out.println();
		for (int i = 1; i <= invoice.size(); i++) {
			System.out.format("%15s%15s%20s%15s%10s%15s%15s", i,
					invoice.get(i - 1).getItemNumber(), invoice.get(i - 1)
							.getItemDescription(), invoice.get(i - 1)
							.getQuantity(), df.format(invoice.get(i - 1)
							.getPrice()), df.format(invoice.get(i - 1)
							.getLineTotal()), invoice.get(i - 1).isTaxable());
			System.out.println();
		}

		// calculate subtotals (taxable subtotals,untaxablesubtotals, tax,
		// grandtotal)

		// call calculateSubtotals method
		double[] subtotals = calculateSubtotals(invoice);
		double taxableSubtotal = subtotals[0];
		double untaxableSubtotal = subtotals[1];

		// call calculateTax method
		double tax = calculateTax(taxableSubtotal, taxRate);

		// call grandtotal method
		double grandTotal = calculateGrandTotal(tax, taxableSubtotal,
				untaxableSubtotal);

		// print out invoice
		System.out.println();
		System.out.format("%65s%25s", "",
				"Taxable Subtotal = " + df.format(taxableSubtotal));
		System.out.println();
		System.out.format("%65s%25s", "",
				"Untaxable Subtotal = " + df.format(untaxableSubtotal));
		System.out.println();
		System.out.format("%65s%25s", "", "Tax = " + df.format(tax));
		System.out.println();
		System.out.format("%65s%25s", "",
				"Grand Total = " + df.format(grandTotal));
	}

	// method to calculate taxable subtotal and untaxable subtotal
	private static double[] calculateSubtotals(ArrayList<LineItem> invoice) {
		double subtotals[] = new double[2];

		// taxable subtotal
		subtotals[0] = 0.0;

		// untaxable subtotal
		subtotals[1] = 0.0;

		// loop through each line of the invoice
		for (int i = 1; i <= invoice.size(); i++) {

			// check if taxable, if yes, update taxable subtotal
			if (invoice.get(i - 1).isTaxable()) {
				subtotals[0] = subtotals[0] + invoice.get(i - 1).getLineTotal();
			}

			// if not, udpate untaxable subtotal
			else {
				subtotals[1] = subtotals[1] + invoice.get(i - 1).getLineTotal();
			}
		}

		return subtotals;
	}

	// method to calculate tax
	private static double calculateTax(double taxableSubtotal, double taxRate) {
		return taxableSubtotal * taxRate;
	}

	// method to calculate Grand Total
	private static double calculateGrandTotal(double tax,
			double taxableSubtotal, double untaxableSubtotal) {
		return tax + taxableSubtotal + untaxableSubtotal;
	}
}
