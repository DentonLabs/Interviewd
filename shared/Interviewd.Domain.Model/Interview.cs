using System.Collections.Generic;

namespace Interviewd.Domain.Model
{
    public class Interview
    {
        public string Id { get; set; }

        public Candidate Candidate { get; set; }

        public IEnumerable<Question> Questions { get; set; }
    }
}