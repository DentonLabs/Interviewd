using System.Collections.Generic;

namespace Interviewd.Domain.Model
{
    public class InterviewTemplate
    {
        public string Id { get; set; }

        public string Name { get; set; }

        public IEnumerable<Question> Questions { get; set; }
    }
}