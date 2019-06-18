using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace muh_clock
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void timer_tick(object sender, EventArgs e)
        {
            lblTime.Text = DateTime.Now.ToString("hh:mm");
            lblSecond.Text = DateTime.Now.ToString("ss");
            lblDate.Text = DateTime.Now.ToString("MMM dd yyyy");
            lblDay.Text = DateTime.Now.ToString("ddd");
        }

        private void form_load(object sender, EventArgs e)
        {
            timer.Start();
        }

        private void LblDate_Click(object sender, EventArgs e)
        {

        }
    }
}
