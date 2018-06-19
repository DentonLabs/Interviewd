namespace Interviewd.Tests.Api.Rest
{
    public class QueryStringBuilder
    {
        private string _QueryString;

        public void AddParameter(string key, string value)
        {
            if (string.IsNullOrEmpty(_QueryString))
            {
                _QueryString += "?";
            }
            else
            {
                _QueryString += "&";
            }

            _QueryString += $"{key}={value}";
        }

        public override string ToString()
        {
            return _QueryString;
        }
    }
}