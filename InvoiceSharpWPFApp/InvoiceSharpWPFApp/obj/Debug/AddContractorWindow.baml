   M S B A M L   `   `   `  ����M  IInvoiceSharpWPFApp, Version=1.0.0.0, Culture=neutral, PublicKeyToken=null7 clr-namespace:InvoiceSharpWPFAppInvoiceSharpWPFApp  5   	   ,    &InvoiceSharpWPFApp.AddContractorWindow   5   	   W SPresentationCore, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35R NWindowsBase, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35\ XPresentationFramework, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35D 9http://schemas.microsoft.com/winfx/2006/xaml/presentation    5   	   R NSystem.Xaml, Version=4.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089:x,http://schemas.microsoft.com/winfx/2006/xaml     5   	   @d2http://schemas.microsoft.com/expression/blend/2008     5   	   Jmc;http://schemas.openxmlformats.org/markup-compatibility/2006     5   	   2local clr-namespace:InvoiceSharpWPFApp     5   	   -   5          � Title$  AddContractorWindow��5   	   $	��600��6%   $	��800��62    � 
ResizeMode$ NoResize=�5	   !   .��5
      � $��20q�6    � ColumnDefinitions 5   
   �� 5      $
��Auto �6   �� 5      $��* �6    � RowDefinitions 5   
   �� 5      $
��Auto �6   �� 5      $
��Auto �6   �� 5      $��80 �6   �� 5      $
��Auto �6   �� 5      $
��Auto �6   �� 5      $
��Auto �6   �� 5      $
��Auto �6   �� 5      $
��Auto �6   .+�5   
   �� $��2��6   $��0��6$   $��Center=�61   $
��Bold"�6N   .��6`   Add new contractor6t   �� 5   
   $��0��6   $��1��6    .��6-   Name:64   �� 5   
   -   5        $��ContractorName��5   A   $��1��6   $��1��6"   $��0 0 0 20q�6/   ��6W   �� *name+6o   �� 5   
   $��0��6   $��2��6    .��6-   
Address:67   �� 5   
   -   5        $��ContractorAddress��5   A   $��1��6   $��2��6"   $��0 0 0 20q�6/    �� AcceptsReturn . ��6o   �� *	address+6�   �� 5   
   $��0��6   $��3��6    .��6-   NIP (Tax ID):6<   �� 5   
   -   5        $��ContractorTaxId��5   A   $��1��6   $��3��6"   $��0 0 0 20q�6/    �� 	MaxLength$ 10��6X   ��5    B   �� *taxId+6[   �� 5!   
   $��0��6   $��4��6    .��6-   REGON:65   �� 5"   
   -   5        $��ContractorRegon��5"   A   $��1��6   $��4��6"   $��0 0 0 20q�6/   $ 14��6X   ��5#   B   �� *regon+6[   �� 5$   
   $��0��6   $��5��6    .��6-   Phone:65   �� 5%   
   -   5        $��ContractorPhone��5%   A   $��1��6   $��5��6"   $��0 0 0 20q�6/   ��5&   B   �� *phone+6[   �� 5'   
   $��0��6   $��6��6    .��6-   	E-mail:66   �� 5(   
   -   5        $��ContractorEmail��5(   A   $��1��6   $��6��6"   $��0 0 0 20q�6/   ��6X   �� *email+6q   �� 5)   
   -   5        $��AddNewContractor��5)   2   $��2��6   $��7��6%   $
��20pxq�6J   .��6w   Add Contractor6�   5*      5-      