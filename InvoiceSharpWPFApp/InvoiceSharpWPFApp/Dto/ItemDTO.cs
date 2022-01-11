using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace InvoiceSharpWPFApp.Dto
{
    public class ItemDTO : INotifyPropertyChanged
    {
        public ItemDTO()
        {
        }

        public string name { get; set; }

        public string pkwiu { get; set; }

        int Quantity;
        public int quantity
        {
            get
            {
                return Quantity;
            }
            set
            {
                Quantity = value;
                netValue = NetPrice * Quantity;
                taxValue = netValue * TaxRate;
                grossValue = netValue + taxValue;
                NotifyPropertyChanged("netValue");
                NotifyPropertyChanged("grossValue");
                NotifyPropertyChanged("taxValue");
            }
        }

        public string unit { get; set; }

        decimal NetPrice;
        public decimal netPrice
        { 
            get
            {
                return NetPrice;
            }
            set
            {
                NetPrice = value;
                netValue = NetPrice * Quantity;
                taxValue = netValue * TaxRate;
                grossValue = netValue + taxValue;
                NotifyPropertyChanged("netValue");
                NotifyPropertyChanged("grossValue");
                NotifyPropertyChanged("taxValue");
            }
        }

        public decimal netValue { get; set; }

        decimal TaxRate;
        public decimal taxRate
        {
            get
            {
                return TaxRate;
            }
            set
            {
                TaxRate = value;
                taxValue = netValue * TaxRate;
                grossValue = netValue + taxValue;
                NotifyPropertyChanged("grossValue");
                NotifyPropertyChanged("taxValue");
            }
        }

        public decimal taxValue { get; set; }

        public decimal grossValue { get; set; }

        public event PropertyChangedEventHandler PropertyChanged;

        public void NotifyPropertyChanged(string propName)
        {
            if (this.PropertyChanged != null)
                this.PropertyChanged(this, new PropertyChangedEventArgs(propName));
        }
    }
}
