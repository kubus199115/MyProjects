   M S B A M L   `   `   `  ����M  IInvoiceSharpWPFApp, Version=1.0.0.0, Culture=neutral, PublicKeyToken=null7 clr-namespace:InvoiceSharpWPFAppInvoiceSharpWPFApp  5   	   1    +InvoiceSharpWPFApp.AddIncomingInvoiceWindow   5   	   W SPresentationCore, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35R NWindowsBase, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35\ XPresentationFramework, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35D 9http://schemas.microsoft.com/winfx/2006/xaml/presentation    5   	   R NSystem.Xaml, Version=4.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089:x,http://schemas.microsoft.com/winfx/2006/xaml     5   	   @d2http://schemas.microsoft.com/expression/blend/2008     5   	   Jmc;http://schemas.openxmlformats.org/markup-compatibility/2006     5   	   2local clr-namespace:InvoiceSharpWPFApp     5   	     � Title$!  Add Incoming Invoice Window��5   	   $	��800��6-   $	��800��6:    � 
ResizeMode$ NoResize=�6F   .��5	      � $��20q�6    � ColumnDefinitions 5
   
   �� 5      $
��Auto �6   �� 5      $��* �6    � RowDefinitions 5   
   �� 5      $
��Auto �6   �� 5      $
��Auto �6   �� 5      $
��Auto �6   �� 5      $
��Auto �6   �� 5      $
��Auto �6   �� 5      $
��Auto �6   �� 5      $
��Auto �6   �� 5      $
��Auto �6   �� 5      $
��Auto �6   �� 5      $
��Auto �6   �� 5      $
��Auto �6   .+�5   
   �� $��2��6   $��0��6$   $��Center=�61   $
��Bold"�6N   $��Add new incoming invoice��6`   � 5   
   $��1��6   $��0��6!   .+�5      �� $	��100��6   .��6    Invoice Number:61   �� 5      $	��200��6   ��6"   �� *invoice.invoiceNumber+5      � 5    
   $��2��6   $��0��6!   .+�5!      �� $	��100��6   .��6    Date of issue:60   (  "System.Windows.Controls.DatePicker  5"      -   5        $��DateOfIssue��5"         SelectedDate 6,   �� *invoice.dateOfIssue+5#      � 5$   
   $��2��6   $��1��6!   $��Right=�61   .+�5%      �� .��6   Place of issue:6%   �� 5&      -   5        $��PlaceOfIssue��5&      $	��200��6*   ��66   �� *invoice.placeOfIssue+5'      � 5(   
   $��3��6   $��0��6!   .+�5)      �� $	��100��6   .��6    Date of sale:6/     5*      -   5        $��
DateOfSale��5*       6+   �� *invoice.dateOfSale+5+      � 5,   
    � Orientation$ Vertical=�6   $��4��6+   $��0��68   .+�5-      �� .��6   Choose contractor:6(   �� 5.      -   5        $��ContractorsComboBox��5.       �� SelectedItem 62   �� *
contractor+5/      � 50   
   $ 
Horizontal=�6   $��4��6-   $��1��6:   $��Center=�6J   .+�51      �� $
��Bold"�6   .��6&   	Seller:6/   �� 52      -   5        $��ContractorName��52      ��6*   �� *contractor.name+53      � 54   
   $��5��6   $��0��6!   .+�55      �� .��6   Method of payment:6(   �� 56      -   5        $��MethodOfPayment��56      $	��100��6.    6:   �� *invoice.methodOfPayment+.@�57      ��  �� 
IsSelected . .��61   Cash67   �� 58      .��6   Bank transfer6.   59      5:      � 5;   
   $��5��6   $��1��6!   $��Right=�61   .+�5<      �� .��6   Date of payment:6&   �� 5=      -   5        $��DateOfPayment��5=      $��40��6+   ��66   �� *invoice.dateOfPayment+�� 5>      .��6   days6   5?      � 5@   
   $��6��6   $��2��6!   $��20q�65   $��Center=�6A   .+�5A      �� .��6   Account Number:6%   �� 5B      -   5        $��AccountNumber��5B      $	��400��6+   ��6:   �� *invoice.accountNumber+5C      &   System.Windows.Controls.DataGrid  5D   
   -	   5        $��Items��5D   4   $��7��6   $��2��6    $��20q�6A      AutoGenerateColumns .  $��Center=�6i   $	��150��5E      $	��150��6    	   Columns	 5F      0  *System.Windows.Controls.DataGridTextColumn  5G      ,  &System.Windows.Controls.DataGridColumn
   Header$

 Name��6%   1  +System.Windows.Controls.DataGridBoundColumn   Binding 63   �� *name+  5H      $
 PKWiU��6%    64   �� *pkwiu+  5I      $
 Quantity��6%    67   �� *
quantity+  5J      $

 Unit��6%    63   �� *unit+  5K      $
 	Net Price��6%    68   �� *
netPrice+  5L      $
 	Net Value��6%      
IsReadOnly .  6J   �� *
netValue+  5M      $
 Tax Rate��6%    67   �� *	taxRate+  5N      $
 	Tax Value��6%    .  6J   �� *
taxValue+  5O      $
 Gross Value��6%    .  6L   �� *
grossValue+5Q      � 5R   
   $ Vertical=�6   $��8��6+   $��0��68   .+�5S      �� .��6   
Remarks:6   �� 5T      -
   5        $��Remarks��5T       �� AcceptsReturn . $��40��6:   $	��200��6F   ��6R   �� *invoice.remarks+5U      �� 5V   
   -   5        $��
AddInvoice��5V   >   $��9��6   $��2��6   $��50q�62   $��Add invoice��6P   6f   �� 5W   
   -   5        $��PrintDetail��5W      $��10��6   $��Print/Detail��62   6I   5X      5Y      